package gr.efood.foodsearcher.model;

public class OpenCageResult {

    private OpenCageLatLng geometry;

    public OpenCageLatLng getGeometry() {
        return geometry;
    }

    @Override
    public String toString() {
        return "OpenCageResult{" +
                "geometry=" + geometry +
                '}';
    }
}
