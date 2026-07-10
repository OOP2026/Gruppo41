package project.arch;

import com.tngtech.archunit.core.domain.JavaClass;
import com.tngtech.archunit.core.domain.JavaClasses;
import com.tngtech.archunit.core.importer.ClassFileImporter;
import org.junit.Test;
import java.util.HashSet;
import java.util.Set;

public class MetricsRunner {

    @Test
    public void calculateMetrics() {
        JavaClasses classes = new ClassFileImporter().importPackages("controller", "dao", "implementazioneDao", "model", "gui");
        
        int totalClasses = classes.size();
        Set<String> packages = new HashSet<>();
        
        for (JavaClass javaClass : classes) {
            packages.add(javaClass.getPackageName());
        }
        
        System.out.println("=== METRICHE DI PROGETTO ===");
        System.out.println("Numero totale di classi analizzate: " + totalClasses);
        System.out.println("Numero totale di pacchetti analizzati: " + packages.size());
        System.out.println("Pacchetti rilevati: " + packages);
    }
}
