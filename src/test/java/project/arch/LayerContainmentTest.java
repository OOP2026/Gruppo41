package project.arch;

import com.tngtech.archunit.core.domain.JavaClasses;
import com.tngtech.archunit.core.importer.ClassFileImporter;
import com.tngtech.archunit.lang.ArchRule;
import static com.tngtech.archunit.lang.syntax.ArchRuleDefinition.classes;
import static com.tngtech.archunit.lang.syntax.ArchRuleDefinition.noClasses;
import org.junit.Test;

public class LayerContainmentTest {

    @Test
    public void testLayerContainmentRules() {
        JavaClasses importedClasses = new ClassFileImporter().importPackages(
            "controller", "dao", "implementazioneDao", "model", "gui"
        );

        // 1. Il pacchetto controller non deve contenere o riferirsi a classi GUI
        ArchRule controllerContainment = noClasses().that().resideInAPackage("..controller..")
                .should().dependOnClassesThat().resideInAPackage("..gui..");
        controllerContainment.check(importedClasses);

        // 2. Il pacchetto model non deve riferirsi a nessun altro strato interno del sistema (indipendenza assoluta)
        ArchRule modelContainment = noClasses().that().resideInAPackage("..model..")
                .should().dependOnClassesThat().resideInAnyPackage(
                    "..gui..", "..controller..", "..dao..", "..implementazioneDao.."
                );
        modelContainment.check(importedClasses);

        // 3. I DAO (interfacce e implementazioni) non devono dipendere dalla GUI o dal Controller
        ArchRule daoContainment = noClasses().that().resideInAPackage("..dao..")
                .should().dependOnClassesThat().resideInAnyPackage("..gui..", "..controller..");
        daoContainment.check(importedClasses);

        ArchRule implDaoContainment = noClasses().that().resideInAPackage("..implementazioneDao..")
                .should().dependOnClassesThat().resideInAnyPackage("..gui..", "..controller..");
        implDaoContainment.check(importedClasses);

        // 4. La GUI può dipendere dal Controller e dal Model per mostrare i dati a schermo, ma non dai DAO diretti
        ArchRule guiContainment = classes().that().resideInAPackage("..gui..")
                .should().onlyDependOnClassesThat().resideInAnyPackage(
                    "..gui..", "..controller..", "..model..", 
                    "java..", "javax..", "org..", "com..", ""
                );
        guiContainment.check(importedClasses);
    }
}
