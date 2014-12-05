package org.ruivieira.gulp;

import org.reflections.Reflections;

import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.util.HashSet;
import java.util.Set;

import static org.reflections.ReflectionUtils.getAllMethods;
import static org.reflections.ReflectionUtils.withModifier;

/**
 * @author Rui Vieira
 * @version 0.1
 * @since 0.1
 */
public class AnnotationProcessor {

    private final String packageName;

    private Set<Reference> references = new HashSet<>();

    public AnnotationProcessor(final String packageName) {
        this.packageName = packageName;
    }

    public void process() {
        Reflections reflections = new Reflections(packageName);

        Set<Class<?>> annotated = reflections.getTypesAnnotatedWith(R.class);

        for (Class clazz : annotated) {
            if (clazz.getAnnotation(ExportClassReference.class) != null) {
                ExportClassReference exportClassReference = (ExportClassReference) clazz.getAnnotation(ExportClassReference.class);
                String reference = exportClassReference.reference();
                // if we are dealing with a Scala class strip the '$'
                if (reference.endsWith("$")) {
                    reference = reference.substring(0, reference.length()-1);
                }
                String namespace = exportClassReference.namespace();
                String classPath = clazz.getName();
                references.add(Reference.createClass(namespace, reference, classPath));
            }
            Set<Method> getters = getAllMethods(clazz,
                    withModifier(Modifier.STATIC));
            for (Method method : getters) {
                ExportMethodReference annotation = method.getAnnotation(ExportMethodReference.class);
                if (annotation != null) {
                    String reference = annotation.reference();
                    String classPath = method.getDeclaringClass().getName();
                    String namespace = annotation.namespace();
                    references.add(Reference.createStaticMethod(namespace, reference, classPath, method.getName()));
                }
            }
        }

    }

    public Set<Reference> getReferences() {
        return references;
    }

}
