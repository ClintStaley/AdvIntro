package com.cstaley.stm3911.Sorting;

public class BinSearch {
	static int binarySearch(int[] vals, int test) {
		int lo, mid, hi;

		lo = mid = 0;
		hi = vals.length;
		while (lo < hi) {
			mid = (lo + hi) / 2;
			if (vals[mid] == test)
				break;
			else if (test > vals[mid])
				lo = mid + 1;
			else
				hi = mid;
		}
		return lo == hi ? -1 : mid;
	}

	public static void main(String[] args) {
		int[] vals = new int[] { 5, 8, 12, 16, 20, 23, 28, 31, 38, 42, 46, 51, 56, 62, 72, 77 };

		System.out.printf("%d at %d\n", 42, binarySearch(vals, 42));
		System.out.printf("%d at %d\n", 43, binarySearch(vals, 43));
	}
}
