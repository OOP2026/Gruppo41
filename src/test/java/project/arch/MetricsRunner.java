package project.arch;

import com.tngtech.archunit.core.domain.JavaClass;
import com.tngtech.archunit.core.domain.JavaClasses;
import com.tngtech.archunit.core.importer.ClassFileImporter;
import com.tngtech.archunit.library.metrics.ArchitectureMetrics;
import com.tngtech.archunit.library.metrics.MetricsComponents;
import org.junit.Test;

public class MetricsRunner {

    @Test
    public void calculateMetrics() {
        JavaClasses classes = new ClassFileImporter().importPackages("controller", "dao", "implementazioneDao", "model", "gui");
        
        MetricsComponents<JavaClass> components = MetricsComponents.from(classes);
        
        int totalClasses = classes.size();
        System.out.println("=== METRICHE DI PROGETTO ===");
        System.out.println("Numero totale di classi analizzate: " + totalClasses);
    }
}
