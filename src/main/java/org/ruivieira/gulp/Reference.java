package org.ruivieira.gulp;

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

    private Reference(String name, String classname, ReferenceType type) {
        this(name, classname, null, type);
    }

    private Reference(String name, String classname, String methodname, ReferenceType type) {
        this.name = name;
        this.classname = classname;
        this.methodname = methodname;
        this.type = type;
    }

    public static Reference createClass(String name, String classname) {
        return new Reference(name, classname, ReferenceType.CLASS);
    }

    public static Reference createStaticMethod(String name, String classname, String methodname) {
        return new Reference(name, classname, methodname, ReferenceType.STATIC);
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

    public ReferenceType getType() {
        return type;
    }
}
