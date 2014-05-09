package org.ruivieira.gulp.processors;

import org.apache.commons.lang.StringUtils;
import org.ruivieira.gulp.Reference;
import org.ruivieira.gulp.ReferenceType;
import org.ruivieira.gulp.TemplateProcessor;

import java.util.ArrayList;
import java.util.List;
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

        builder.append("\treturn(list(\n");

        List<String> referencesItems = new ArrayList<>();
        for (Reference reference : references) {
            StringBuilder referenceItem = new StringBuilder();

            referenceItem.append("\t\t").append(reference.getName()).append(" = J(\"").append(reference.getClassname());
            if (reference.getType().equals(ReferenceType.CLASS)) {
                referenceItem.append("\")");
            } else if (reference.getType().equals(ReferenceType.STATIC)) {
                referenceItem.append("\")$").append(reference.getMethodname());
            }
            referencesItems.add(referenceItem.toString());
        }
        builder.append(StringUtils.join(referencesItems, ",\n"));
        builder.append("\n\t))\n");

        builder.append("}");
        return builder.toString();
    }
}
