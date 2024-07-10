package Trabalho;

   class SortResult {
        long swaps;
        long comparisons;
        long time; 

        SortResult(long swaps, long comparisons, long time) {
            this.swaps = swaps;
            this.comparisons = comparisons;
            this.time = time;
        }

        double getTimeInSeconds() {
            return time / 1000.0; 
        }
    }