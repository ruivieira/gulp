package org.ruivieira.gulp;

/**
 * @author Rui Vieira
 */
public class Reference {

    private final String name;
    private final String value;

    private Reference(String name, String value) {
        this.name = name;
        this.value = value;
    }

    public static Reference create(String name, String value) {
        return new Reference(name, value);
    }

    public String getName() {
        return name;
    }

    public String getValue() {
        return value;
    }

}
