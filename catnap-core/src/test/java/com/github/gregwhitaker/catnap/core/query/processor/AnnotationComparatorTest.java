package com.github.gregwhitaker.catnap.core.query.processor;

import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import com.github.gregwhitaker.catnap.core.exception.CatnapException;
import com.github.gregwhitaker.catnap.core.util.ClassUtil;
import org.junit.Test;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import static org.junit.Assert.assertEquals;

/**
 * Unit tests verifying correct sorting of fields based on both the {@link com.github.gregwhitaker.catnap.core.annotation.CatnapOrder}
 * and {@link com.fasterxml.jackson.annotation.JsonPropertyOrder} annotations.
 */
public class AnnotationComparatorTest {

    @JsonPropertyOrder({
            "second",
            "first"
    })
    static class BeanWithJsonPropertyOrder {
        private String first = "first";
        private String second = "second";

        public String getFirst() { return first; }

        public String getSecond() { return second; }
    }

    static class BeanWithoutOrderAnnotation {
        private String first = "first";
        private String second = "second";

        public String getFirst() { return first; }

        public String getSecond() { return second; }
    }

    @JsonPropertyOrder(value = {
            "second",
            "first"
    }, alphabetic = true)
    static class BeanWithOrphans {
        private String first = "first";
        private String second = "second";
        private String orphanC = "orphanC";
        private String orphanB = "orphanB";
        private String orphanA = "orphanA";

        public String getFirst() { return first; }

        public String getSecond() { return second; }

        public String getOrphanC() { return orphanC; }

        public String getOrphanB() { return orphanB; }

        public String getOrphanA() { return orphanA; }
    }

    @Test
    public void compareJsonPropertyOrderLessThan() {
        AnnotationComparator<BeanWithJsonPropertyOrder> comparator = new AnnotationComparator<>(BeanWithJsonPropertyOrder.class);

        BeanWithJsonPropertyOrder model = new BeanWithJsonPropertyOrder();
        Property<BeanWithJsonPropertyOrder> first = new CatnapProperty<>(model,
                ClassUtil.getReadableProperty("first", BeanWithJsonPropertyOrder.class));
        Property<BeanWithJsonPropertyOrder> second = new CatnapProperty<>(model,
                ClassUtil.getReadableProperty("second", BeanWithJsonPropertyOrder.class));

        assertEquals(-1, comparator.compare(second, first));
    }

    @Test
    public void compareJsonPropertyOrderEquals() {
        AnnotationComparator<BeanWithJsonPropertyOrder> comparator = new AnnotationComparator<>(BeanWithJsonPropertyOrder.class);

        BeanWithJsonPropertyOrder model = new BeanWithJsonPropertyOrder();
        Property<BeanWithJsonPropertyOrder> first = new CatnapProperty<>(model,
                ClassUtil.getReadableProperty("first", BeanWithJsonPropertyOrder.class));

        assertEquals(0, comparator.compare(first, first));
    }

    @Test
    public void compareJsonPropertyOrderGreaterThan() {
        AnnotationComparator<BeanWithJsonPropertyOrder> comparator = new AnnotationComparator<>(BeanWithJsonPropertyOrder.class);

        BeanWithJsonPropertyOrder model = new BeanWithJsonPropertyOrder();
        Property<BeanWithJsonPropertyOrder> first = new CatnapProperty<>(model,
                ClassUtil.getReadableProperty("first", BeanWithJsonPropertyOrder.class));
        Property<BeanWithJsonPropertyOrder> second = new CatnapProperty<>(model,
                ClassUtil.getReadableProperty("second", BeanWithJsonPropertyOrder.class));

        assertEquals(1, comparator.compare(first, second));
    }

    @Test(expected = CatnapException.class)
    public void verifyBeanWithMissingOrderAnnotationThrowsException() {
        AnnotationComparator<BeanWithoutOrderAnnotation> comparator = new AnnotationComparator<>(BeanWithoutOrderAnnotation.class);

        BeanWithoutOrderAnnotation model = new BeanWithoutOrderAnnotation();
        Property<BeanWithoutOrderAnnotation> first = new CatnapProperty<>(model,
                ClassUtil.getReadableProperty("first", BeanWithoutOrderAnnotation.class));
        Property<BeanWithoutOrderAnnotation> second = new CatnapProperty<>(model,
                ClassUtil.getReadableProperty("second", BeanWithoutOrderAnnotation.class));

        comparator.compare(first, second);
    }

    @Test
    public void verifyOrphanFieldsAreSortedAlphabetically() {
        BeanWithOrphans model = new BeanWithOrphans();
        List<Property<BeanWithOrphans>> props = new ArrayList<Property<BeanWithOrphans>>();

        props.add(new CatnapProperty<>(model, ClassUtil.getReadableProperty("first", BeanWithOrphans.class)));
        props.add(new CatnapProperty<>(model, ClassUtil.getReadableProperty("second", BeanWithOrphans.class)));
        props.add(new CatnapProperty<>(model, ClassUtil.getReadableProperty("orphanA", BeanWithOrphans.class)));
        props.add(new CatnapProperty<>(model, ClassUtil.getReadableProperty("orphanB", BeanWithOrphans.class)));
        props.add(new CatnapProperty<>(model, ClassUtil.getReadableProperty("orphanC", BeanWithOrphans.class)));

        Collections.sort(props, new AnnotationComparator<>(BeanWithOrphans.class));

        assertEquals("second", props.get(0).getName());
        assertEquals("first", props.get(1).getName());
        assertEquals("orphanA", props.get(2).getName());
        assertEquals("orphanB", props.get(3).getName());
        assertEquals("orphanC", props.get(4).getName());
    }
}
