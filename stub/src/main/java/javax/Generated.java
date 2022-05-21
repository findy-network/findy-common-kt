package javax.annotation;

// Dummy implementation for to avoid actual javax.annotation package 
public @interface Generated {
    String[] value();

    String date() default "";

    String comments() default "";
}
