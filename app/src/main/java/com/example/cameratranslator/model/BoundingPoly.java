package com.example.cameratranslator.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class BoundingPoly {
    @SerializedName("vertices")
    @Expose
    private List<Vertex> vertices;

    @SerializedName("normalizedVertices")
    @Expose
    private List<NormalizedVertice> normalizedVertices;

    public BoundingPoly(List<Vertex> vertices) {
        this.vertices = vertices;
    }

    public List<Vertex> getVertices() {
        return vertices;
    }

    public void setVertices(List<Vertex> vertices) {
        this.vertices = vertices;
    }

    public List<NormalizedVertice> getNormalizedVertices() {
        return normalizedVertices;
    }

    public void setNormalizedVertices(List<NormalizedVertice> normalizedVertices) {
        this.normalizedVertices = normalizedVertices;
    }

    public String normalizedVerticesData() {
        StringBuilder data = new StringBuilder("[");
        for (NormalizedVertice vertex : normalizedVertices) {
            data
                    .append("( ")
                    .append(vertex.x)
                    .append(" , ")
                    .append(vertex.y)
                    .append(" )");
        }
        data.append("]\n");

        return data.toString();
    }

    public static class NormalizedVertice {

        private float x;
        private float y;

        public float getX() {
            return x;
        }

        public void setX(float x) {
            this.x = x;
        }

        public float getY() {
            return y;
        }

        public void setY(float y) {
            this.y = y;
        }
    }

    public static class Vertex {

        private int x;
        private int y;

        public int getX() {
            return x;
        }

        public void setX(int x) {
            this.x = x;
        }

        public int getY() {
            return y;
        }

        public void setY(int y) {
            this.y = y;
        }
    }
}
