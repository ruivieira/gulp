package org.ruivieira.gulp;

import org.reflections.ReflectionUtils;

import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.util.Set;

/**
 * @author Rui Vieira
 * @version 0.1
 * @since 0.1
 */
public class Reference {

    private final String name;
    private final ReferenceType type;
    private String classname;
    private String methodname;
    private String namespace;

    private Reference(String namespace, String name, String classname, ReferenceType type) {
        this(namespace, name, classname, null, type);
    }

    private Reference(String namespace, String name, String classname, String methodname, ReferenceType type) {
        this.name = name;
        this.classname = classname;
        this.methodname = methodname;
        this.type = type;
        this.namespace = namespace;
    }

    public static Reference createClass(String name, String classname) {
        return new Reference("", name, classname, ReferenceType.CLASS);
    }

    public static Reference createClass(String namespace, String name, String classname) {
        return new Reference(namespace, name, classname, ReferenceType.CLASS);
    }

    public static Reference createStaticMethod(String name, String classname, String methodname) {
        return new Reference("", name, classname, methodname, ReferenceType.STATIC);
    }

    public static Reference createStaticMethod(String namespace, String name, String classname, String methodname) {
        return new Reference(namespace, name, classname, methodname, ReferenceType.STATIC);
    }

    public static Reference createClass(String name, Class clazz) {
        return new Reference("", name, clazz.getName(), ReferenceType.CLASS);
    }

    public static Reference createClass(String namespace, String name, Class clazz) {
        return new Reference(namespace, name, clazz.getName(), ReferenceType.CLASS);
    }

    public static Reference createStaticMethod(String name, Class clazz, String methodname) {

        Set<Method> methods = ReflectionUtils.getMethods(clazz, ReflectionUtils.withModifier(Modifier.PUBLIC), ReflectionUtils.withModifier(Modifier.STATIC),
                ReflectionUtils.withName(methodname));
        if (methods.size() == 0) {
            StringBuilder exceptionMsg = new StringBuilder();
            exceptionMsg.append("There is no method with that name [");
            exceptionMsg.append(clazz.getName()).append(".").append(methodname).append("]");
            throw new RuntimeException(exceptionMsg.toString());
        }

        return new Reference(name, clazz.getName(), methodname, ReferenceType.STATIC);

    }

    public String getClassname() {
        return classname;
    }

    public String getMethodname() {
        return methodname;
    }

    public String getName() {
        return name;
    }

    public String getQualifiedName() {
        final StringBuilder builder = new StringBuilder();
        if (!namespace.equals("")) {
            builder.append(namespace).append(".");
        }
        builder.append(name);
        return builder.toString();
    }

    public String getFormatedBinding() {
        final StringBuilder builder = new StringBuilder();
        builder.append("J('").append(getClassname()).append("')");
        if (getType().equals(ReferenceType.STATIC)) {
            builder.append("$").append(getMethodname());
        }
        return builder.toString();
    }

    public ReferenceType getType() {
        return type;
    }
}
