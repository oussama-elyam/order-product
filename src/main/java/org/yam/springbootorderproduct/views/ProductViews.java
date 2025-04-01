package org.yam.springbootorderproduct.views;

public class ProductViews {
    public interface Public {}  // Minimal fields for public API
    public interface Internal extends Public {}  // Extended details for internal use
}