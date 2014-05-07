package org.ruivieira.gulp;

import java.lang.annotation.*;

/**
 * @author Rui Vieira
 */
@Documented
@Target(ElementType.METHOD)
@Inherited
@Retention(RetentionPolicy.RUNTIME)
public @interface ExportMethodReference {
    String reference();
}
