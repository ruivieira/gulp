package org.ruivieira.gulp;

import java.util.Set;

/**
 * @author Rui Vieira
 */
public interface TemplateProcessor {

    String process(Set<Reference> references);

}
