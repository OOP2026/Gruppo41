package project.arch;

import com.tngtech.archunit.junit.AnalyzeClasses;
import com.tngtech.archunit.junit.ArchTest;
import com.tngtech.archunit.junit.ArchUnitRunner;
import com.tngtech.archunit.lang.ArchRule;
import org.junit.runner.RunWith;

import static com.tngtech.archunit.lang.syntax.ArchRuleDefinition.classes;
import static com.tngtech.archunit.lang.syntax.ArchRuleDefinition.noClasses;

@RunWith(ArchUnitRunner.class)
@AnalyzeClasses(packages = {"controller", "dao", "database_connection", "gui", "implementazioneDao", "model"})
public class LayerContainmentTest {

    @ArchTest
    public static final ArchRule controllers_reside_in_the_controller_layer = classes()
            .that().haveSimpleNameEndingWith("Controller")
            .should().resideInAPackage("controller..");

    @ArchTest
    public static final ArchRule controllers_only_reside_in_the_controller_layer = noClasses()
            .that().haveSimpleNameNotEndingWith("Controller")
            .should().resideInAPackage("controller..");

    @ArchTest
    public static final ArchRule DAOs_reside_in_the_DAO_layer = classes()
            .that().haveSimpleNameEndingWith("DAO")
            .and().haveSimpleNameNotEndingWith("Dao")
            .should().resideInAPackage("dao..");

    @ArchTest
    public static final ArchRule DAOs_only_reside_in_the_DAO_layer = noClasses()
            .that().haveSimpleNameNotEndingWith("DAO")
            .should().resideInAPackage("dao..");

    @ArchTest
    public static final ArchRule DAO_implementations_reside_in_the_DAO_implementation_layer = classes()
            .that().haveSimpleNameEndingWith("PostgresDAO")
            .should().resideInAPackage("implementazioneDao..");

    @ArchTest
    public static final ArchRule GUIs_reside_in_the_GUI_layer = classes()
            .that().haveSimpleNameEndingWith("Frame")
            .should().resideInAPackage("gui..");
}
