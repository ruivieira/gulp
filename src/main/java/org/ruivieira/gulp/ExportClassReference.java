package org.ruivieira.gulp;

import java.lang.annotation.*;

/**
 * @author Rui Vieira
 * @version 0.1
 * @since 0.1
 */

@Documented
@Target(ElementType.TYPE)
@Inherited
@Retention(RetentionPolicy.RUNTIME)
public @interface ExportClassReference {
    String reference();
}
