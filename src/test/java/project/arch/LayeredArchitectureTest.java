package project.arch;

import com.tngtech.archunit.core.domain.JavaClasses;
import com.tngtech.archunit.core.importer.ClassFileImporter;
import com.tngtech.archunit.lang.ArchRule;
import static com.tngtech.archunit.lang.syntax.ArchRuleDefinition.classes;
import static com.tngtech.archunit.lang.syntax.ArchRuleDefinition.noClasses;
import org.junit.Test;

public class ArchitectureTest {

    @Test
    public void testLayeredArchitecture() {
        JavaClasses importedClasses = new ClassFileImporter().importPackages("controller", "dao", "implementazioneDao", "model", "gui");

        // Regola 1: Il Model non deve dipendere da nessun altro pacchetto interno
        ArchRule modelRule = noClasses().that().resideInAPackage("..model..")
                .should().dependOnClassesThat().resideInAnyPackage("..gui..", "..controller..", "..implementazioneDao..", "..dao..");
        modelRule.check(importedClasses);

        // Regola 2: La GUI può dipendere sia dal Controller che dal Model (per mostrare le lezioni a schermo)
        ArchRule guiRule = classes().that().resideInAPackage("..gui..")
                .should().onlyDependOnClassesThat().resideInAnyPackage("..gui..", "..controller..", "..model..", "java..", "javax..", "org..", "com..", "");
        guiRule.check(importedClasses);

        // Regola 3: Il Controller non deve dipendere dalla GUI
        ArchRule controllerRule = noClasses().that().resideInAPackage("..controller..")
                .should().dependOnClassesThat().resideInAPackage("..gui..");
        controllerRule.check(importedClasses);

        // Regola 4: I DAO non devono dipendere dalla GUI o dal Controller
        ArchRule daoRule = noClasses().that().resideInAPackage("..dao..")
                .should().dependOnClassesThat().resideInAnyPackage("..gui..", "..controller..");
        daoRule.check(importedClasses);

        // Regola 5: Le classi di implementazione dei DAO devono risiedere nel pacchetto corretto
        ArchRule implRule = classes().that().resideInAPackage("..implementazioneDao..")
                .should().onlyDependOnClassesThat().resideInAnyPackage("..implementazioneDao..", "..dao..", "..model..", "..database_connection..", "java..", "javax..", "org..", "com..", "");
        implRule.check(importedClasses);
    }
}
