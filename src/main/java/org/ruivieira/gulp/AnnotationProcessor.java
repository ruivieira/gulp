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

    public void process(String packageName) {
        Reflections reflections = new Reflections(packageName);

        Set<Class<?>> annotated = reflections.getTypesAnnotatedWith(R.class);

        for (Class clazz : annotated) {
            if (clazz.getAnnotation(ExportClassReference.class) != null) {
                ExportClassReference exportClassReference = (ExportClassReference) clazz.getAnnotation(ExportClassReference.class);
                String reference = exportClassReference.reference();
                String classPath = clazz.getName();
                references.add(Reference.createClass(reference, classPath));
            }
            Set<Method> getters = getAllMethods(clazz,
                    withModifier(Modifier.STATIC));
            for (Method method : getters) {
                ExportMethodReference annotation = method.getAnnotation(ExportMethodReference.class);
                if (annotation != null) {
                    String reference = annotation.reference();
                    String classPath = method.getDeclaringClass().getName();
                    references.add(Reference.createStaticMethod(reference, classPath, method.getName()));
                }
            }
        }

    }

    public Set<Reference> getReferences() {
        return references;
    }

}
