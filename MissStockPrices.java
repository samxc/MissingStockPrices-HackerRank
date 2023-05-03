/*
The interpolateMissingValues method takes as input two arrays: times, which represents the times of the data points, and prices, which represents the prices at those times. The method returns a new array interpolatedPrices that contains the same data as the prices array, but with missing values replaced by estimated values calculated using linear interpolation.

Here’s a detailed explanation of what the method does:

The method creates a new array interpolatedPrices to store the interpolated prices.
The method iterates through the prices array and checks if each value is NaN, which represents a missing value.
If a missing value is found, the method searches for the nearest known values on either side of it. This is done by iterating backwards and forwards from the missing value until known values are found.
If known values are found on either side of the missing value, the method uses them to calculate the missing value using the formula for linear interpolation: y = y1 + (x - x1) * (y2 - y1) / (x2 - x1). In this formula, x1 and x2 represent the times of the known values on either side of the missing value, y1 and y2 represent the corresponding known prices, and x represents the time of the missing value.
The calculated missing value is stored in the interpolatedPrices array at the same index as the original missing value.
If a value in the prices array is not missing, its value is copied to the interpolatedPrices array without modification.
After all missing values have been calculated, the method returns the interpolatedPrices array.
*/


public static void calcMissing(List<String> readings) {
    int n = readings.size();
    double[] day = new double[n];
    double[] values = new double[n];
    double sum = 0;
    int count = 0;
    for (int i = 0; i < n; i++) {
        String[] parts = readings.get(i).split("\t");
        day[i] = i;
        if (parts[1].startsWith("Missing")) {
            values[i] = Double.NaN;
        } else {
            values[i] = Double.parseDouble(parts[1]);
            sum += values[i];
            count++;
        }
    }

    double average = sum / count;

    double[] interpolatedValue = interpolateMissingValues(day, values);

    for (int i = 0; i < n; i++) {
        if (Double.isNaN(values[i])) {
            if (!Double.isNaN(interpolatedValue[i])) {
                System.out.println(interpolatedValue[i]);
            } else {
                System.out.println(average);
            }
        }
    }
}

public static double[] interpolateMissingValues(double[] day, double[] values) { // you will need this helper function
    double[] interpolatedValue = new double[values.length];
    for (int i = 0; i < values.length; i++) {
        if (Double.isNaN(values[i])) {
            int j = i - 1;
            while (j >= 0 && Double.isNaN(values[j])) {
                j--;
            }
            int k = i + 1;
            while (k < values.length && Double.isNaN(values[k])) {
                k++;
            }
            if (j >= 0 && k < values.length) {
                double x1 = day[j];
                double x2 = day[k];
                double y1 = values[j];
                double y2 = values[k];
                double x = day[i];
                double y = y1 + (x - x1) * (y2 - y1) / (x2 - x1);
                interpolatedValue[i] = y;
            } else {
                interpolatedValue[i] = Double.NaN;
            }
        } else {
            interpolatedValue[i] = values[i];
        }
    }
    return interpolatedValue;
}
