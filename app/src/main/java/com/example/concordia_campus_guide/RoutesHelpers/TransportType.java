package com.example.concordia_campus_guide.RoutesHelpers;

public enum TransportType {
    TRANSIT {
        @Override
        public String toString() {
            return "transit";
        }
    }, WALKING {
        @Override
        public String toString() {
            return "walking";
        }
    }, BICYCLING {
        @Override
        public String toString() {
            return "bicycling";
        }
    }, DRIVING {
        @Override
        public String toString() {
            return "driving";
        }
    };

    public abstract String toString();
}


