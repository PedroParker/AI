package org.ai;

class Data {
    float x1;
    float x2;
    float y;

    Data(float x1, float x2, float y) {
        this.x1 = x1;
        this.x2 = x2;
        this.y = y;
    }
}

class Weight {
    float w1;
    float b1;
    float w2;
    float b2;

    Weight(float w1, float b1, float w2, float b2) {
        this.w1 = w1;
        this.b1 = b1;
        this.w2 = w2;
        this.b2 = b2;
    }
}

class Diff {
    float d1;
    float d2;

    Diff(float d1, float d2) {
        this.d1 = d1;
        this.d2 = d2;
    }
}

public class Main {
    static Data[] train_data = new Data[]{
        new Data(1, 1, 1),
        new Data(1, 0, 0),
        new Data(0, 1, 0),
        new Data(0, 0, 0),
    };

    public static void main(String[] args) {
        Weight w = new Weight(0.0f, -2.0f, 5.0f, 2.0f);

        float eps = 1e-1f;
        float rate = 1e-1f;

        System.out.println("Loss: " + loss(w));

        for (int i = 0; i < 1000; i++) {
            apply(w, eps, rate);
            // System.out.println("model: " + model(w, train_data[3]));
            // System.out.println("model: " + activate((float) i));
        }

        System.out.println("Loss: " + loss(w));
    }

    static float loss(Weight w) {
        float result = 0;

        for (Data data : train_data) {
            float y = model(w, data);
            float diff = data.y - y;

            result += diff * diff;
        }

        return result;
    }

    static float activate(float value) {
        return 1.0f / (1.0f + (float) Math.exp(-value));
    }

    static float model(Weight w, Data data) {
        float y = data.x1 * w.w1 + w.b1 + data.x2 * w.w2 + w.b2;
        return activate(y);
    }

    static void apply(Weight w, float eps, float rate) {
        float f = loss(w);

        float v1 = (loss(new Weight(w.w1 + eps, w.b1, w.w2, w.b2)) - f) / eps;
        float v2 = (loss(new Weight(w.w1, w.b1 + eps, w.w2, w.b2)) - f) / eps;
        float v3 = (loss(new Weight(w.w1, w.b1, w.w2 + eps, w.b2)) - f) / eps;
        float v4 = (loss(new Weight(w.w1, w.b1, w.w2, w.b2 + eps)) - f) / eps;

        w.w1 -= rate * v1;
        w.b1 -= rate * v2;
        w.w2 -= rate * v3;
        w.b2 -= rate * v4;
    }
}
