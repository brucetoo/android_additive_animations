package at.wirecube.additiveanimations.additive_animator;

import android.animation.TypeEvaluator;
import android.graphics.Path;
import android.util.Property;
import android.view.View;

public class PropertyDescription {

    enum PathMode {
        X, Y, ROTATION;
        public static PathMode from(int mode) {
            switch (mode) {
                case 1: return Y;
                case 2: return ROTATION;
                case 0: default: return X;
            }
        }
    }

    private String mTag;
    private float mStartValue;
    private float mTargetValue;
    private Property<View, Float> mProperty;
    private Path mPath;
    private PathMode mPathMode;
    private TypeEvaluator mCustomTypeEvaluator;

    /**
     * The preferred constructor to use when animating properties. If you use this constructor, you
     * don't need to worry about the logic to apply the changes. This is taken care of by using the
     * Setter provided by `property`.
     */
    public PropertyDescription(Property<View, Float> property, float startValue, float targetValue) {
        mProperty = property;
        mTargetValue = targetValue;
        mStartValue = startValue;
    }

    /**
     * Use this constructor for custom properties that have no simple getter or setter.
     * @param tag Name of the animated property. Must be unique.
     * @param startValue Start value of the animated property.
     * @param targetValue Target value of the animated property.
     */
    public PropertyDescription(String tag, float startValue, float targetValue) {
        this.mTag = tag;
        this.mStartValue = startValue;
        this.mTargetValue = targetValue;
    }

    public PropertyDescription(String tag, float startValue, Path path, PathMode pathMode, PathEvaluator sharedEvaluator) {
        this.mTag = tag;
        this.mStartValue = startValue;
        this.mPath = path;
        this.mCustomTypeEvaluator = sharedEvaluator;
        this.mPathMode = pathMode;
        this.mTargetValue = (float) mCustomTypeEvaluator.evaluate(1f, mPathMode, mPath);
    }

    public PropertyDescription(Property<View, Float> property, float startValue, Path path, PathMode pathMode, PathEvaluator sharedEvaluator) {
        this.mProperty = property;
        this.mStartValue = startValue;
        this.mPath = path;
        this.mCustomTypeEvaluator = sharedEvaluator;
        this.mPathMode = pathMode;
        this.mTargetValue = (float) mCustomTypeEvaluator.evaluate(1f, mPathMode, mPath);
    }

    public String getTag() {
        return mProperty != null ? mProperty.getName() : mTag;
    }

    public float getStartValue() {
        return mStartValue;
    }

    public float getTargetValue() {
        return mTargetValue;
    }

    public void setStartValue(float startValue) {
        this.mStartValue = startValue;
    }

    public void setCustomTypeEvaluator(TypeEvaluator<Float> evaluator) {
        mCustomTypeEvaluator = evaluator;
    }

    public TypeEvaluator getCustomTypeEvaluator() {
        return mCustomTypeEvaluator;
    }

    public Property<View, Float> getProperty() { return mProperty; }

    public Path getPath() {
        return mPath;
    }

    public PathMode getPathMode() {
        return mPathMode;
    }

    @Override
    public int hashCode() {
        if(mTag != null) {
            return mTag.hashCode();
        } else {
            return mProperty.getName().hashCode();
        }
    }

    @Override
    public boolean equals(Object o) {
        if(!(o instanceof PropertyDescription)) {
            return false;
        }
        PropertyDescription other = (PropertyDescription) o;
        if(other.mTag != null && mTag != null) {
            return other.mTag.equals(mTag);
        } else if(other.mProperty != null && mProperty != null) {
            return other.mProperty.getName().equals(mProperty.getName());
        } else {
            return false;
        }
    }
}