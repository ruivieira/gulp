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

    private Set<Reference> referenceMap = new HashSet<>();

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
                System.out.println(classPath + " [" + reference + "] is annotated with @ExportClassReference");
                referenceMap.add(Reference.create(reference, classPath));
            }
            Set<Method> getters = getAllMethods(clazz,
                    withModifier(Modifier.STATIC));
            for (Method method : getters) {
                String reference = method.getAnnotation(ExportMethodReference.class).reference();
                String classPath = method.getDeclaringClass().getName() + "." + method.getName();
                referenceMap.add(Reference.create(reference, classPath));
                System.out.println(classPath + " [" + reference + "]is annotated with @ExportMethodReference");
            }
        }

    }

    public Set<Reference> getReferenceMap() {
        return referenceMap;
    }

}
