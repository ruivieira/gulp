package org.ruivieira.gulp.processors;

import org.ruivieira.gulp.Reference;
import org.ruivieira.gulp.TemplateProcessor;

import java.util.Set;

/**
 * @author Rui Vieira
 */
public class StringBuilterTemplateProcessor implements TemplateProcessor {

    @Override
    public String process(Set<Reference> references) {
        StringBuilder builder = new StringBuilder();
        builder.append("library(rJava)\n\n");
        builder.append("init <- function(classpath=c()) {\n");
        builder.append("\tif (!is.null(classpath)) {\n\n");

        builder.append("\t\t# initialise the JVM\n");
        builder.append("\t\t\t.jinit()\n\n");

        builder.append("\t\tfor (path in classpath) {\n");
        builder.append("\t\t\textension <- file_ext(path)\n");
        builder.append("\t\t\tif (extension==\"\") {\n");
        builder.append("\t\t\t\t.jaddClassPath(dir(path, full.names=TRUE ))\n");
        builder.append("\t\t\t} else {\n");
        builder.append("\t\t\t\t.jaddClassPath(path)\n");
        builder.append("\t\t\t}\n");
        builder.append("\t\t}\n");
        builder.append("\t}\n\n");
        for (Reference reference : references) {
            builder.append("\t")
                    .append(reference.getName()).append(" <- J(\"").append(reference.getValue()).append("\")\n");
        }
        builder.append("}");
        return builder.toString();
    }
}
