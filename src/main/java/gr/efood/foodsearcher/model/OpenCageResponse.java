package gr.efood.foodsearcher.model;

import java.util.List;

public class OpenCageResponse {

    private List<OpenCageResult> results;
    private OpenCageStatus status;

    public List<OpenCageResult> getResults() {
        return results;
    }

    public OpenCageStatus getStatus() {
        return status;
    }

    @Override
    public String toString() {
        return "OpenCageResponse{" +
                "results=" + results +
                ", status=" + status +
                '}';
    }
}
