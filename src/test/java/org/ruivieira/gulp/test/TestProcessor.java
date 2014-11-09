package org.ruivieira.gulp.test;

import org.ruivieira.gulp.AnnotationProcessor;
import org.ruivieira.gulp.TemplateProcessor;
import org.ruivieira.gulp.processors.StringBuilterTemplateProcessor;

/**
 * @author Rui Vieira
 * @since 0.1
 */
public class TestProcessor {
    public static void main(String[] args) {
        AnnotationProcessor processor = new AnnotationProcessor("org.ruivieira.gulp");
        processor.process();

        TemplateProcessor myProcessor = new StringBuilterTemplateProcessor();
        String result = myProcessor.process(processor.getReferences());
        System.out.println(result);
    }
}
