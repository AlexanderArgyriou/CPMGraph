package models.activities;

import java.util.Objects;

public class Activity extends ActivityAbstract {
    private String name = "";

    public Activity(String name, int duration) {
        super( duration );
        this.name = name;
    }

    public Activity(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public void printModel() {
        System.out.println( this.toString() );
    }

    @Override
    public boolean equals(Object o) {
        if ( this == o ) return true;
        if ( o == null || getClass() != o.getClass() ) return false;
        Activity activity = ( Activity ) o;
        return name.equals( activity.name );
    }

    @Override
    public int hashCode() {
        return Objects.hash( name ); // just name no super, key to graph node.
    }

    @Override
    public String toString() {
        return "Activity{" +
                "name='" + name + '\'' +
                "} " + super.toString();
    }
}
