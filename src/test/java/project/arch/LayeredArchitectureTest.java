package project.arch;

import com.tngtech.archunit.core.domain.JavaClasses;
import com.tngtech.archunit.core.importer.ClassFileImporter;
import com.tngtech.archunit.lang.ArchRule;
import static com.tngtech.archunit.lang.syntax.ArchRuleDefinition.classes;
import static com.tngtech.archunit.lang.syntax.ArchRuleDefinition.noClasses;
import org.junit.Test;

public class LayeredArchitectureTest {

    @Test
    public void testLayeredArchitecture() {
        JavaClasses importedClasses = new ClassFileImporter().importPackages(
            "controller", "dao", "implementazioneDao", "model", "gui"
        );

        // 1. La GUI può dipendere dal Controller e dal Model (necessario per leggere e mostrare i dati delle lezioni)
        ArchRule guiRule = classes().that().resideInAPackage("..gui..")
                .should().onlyDependOnClassesThat().resideInAnyPackage(
                    "..gui..", "..controller..", "..model..", 
                    "java..", "javax..", "org..", "com..", ""
                );
        guiRule.check(importedClasses);

        // 2. Il Controller non deve dipendere dalla GUI per garantire il disaccoppiamento
        ArchRule controllerRule = noClasses().that().resideInAPackage("..controller..")
                .should().dependOnClassesThat().resideInAPackage("..gui..");
        controllerRule.check(importedClasses);

        // 3. I DAO non devono dipendere in alcun modo dalla GUI o dal Controller
        ArchRule daoRule = noClasses().that().resideInAPackage("..dao..")
                .should().dependOnClassesThat().resideInAnyPackage("..gui..", "..controller..");
        daoRule.check(importedClasses);

        ArchRule implDaoRule = noClasses().that().resideInAPackage("..implementazioneDao..")
                .should().dependOnClassesThat().resideInAnyPackage("..gui..", "..controller..");
        implDaoRule.check(importedClasses);

        // 4. Il Model deve essere indipendente da tutte le altre componenti logiche e di persistenza
        ArchRule modelRule = noClasses().that().resideInAPackage("..model..")
                .should().dependOnClassesThat().resideInAnyPackage(
                    "..gui..", "..controller..", "..dao..", "..implementazioneDao.."
                );
        modelRule.check(importedClasses);
    }
}
