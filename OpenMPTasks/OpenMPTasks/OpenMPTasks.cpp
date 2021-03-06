#include "stdafx.h"
#include "MultiplyMatrix.h"
#include "omp.h"
#include <iostream>
#include <algorithm>

double get_trunclated_average(size_t size, double* numbers) 
{
	double sum = 0;
	double count = 0;

	std::sort(numbers, numbers + size);

	if (size < 5) {
		return numbers[size / 2];
	}

	for (int i = size / (size / 2); i < size - size / (size / 2); ++i)
	{
		sum += numbers[i];
		++count;
	}

	return count != 0 ? sum / count : 0;
}

int main()
{
	bool exit = true;
	while (exit)
	{
		size_t size = 1000;
		size_t count_execution = 10;

		std::cout << "Enter size matrix : ";
		std::cin >> size;
		std::cout << "Enter count execution : ";
		std::cin >> count_execution;

		int* matrix1 = new int[size * size];
		int* matrix2 = new int[size * size];
		int* result = new int[size * size];

		fill_matrix_random(size, matrix1);
		fill_matrix_random(size, matrix2);
		
		double* times = new double[count_execution];

		std::cout << "Wait..." << std::endl;

		for (rsize_t i = 0; i < count_execution; ++i)
		{
			double start = omp_get_wtime();
			multiply_matrix_consistent(size, matrix1, matrix2, result);
			double end = omp_get_wtime();
			times[i] = end - start;
		}

		std::cout << "Consistent execution : " << get_trunclated_average(count_execution, times) << std::endl;

		std::cout << "Wait..." << std::endl;

		for (rsize_t i = 0; i < count_execution; ++i)
		{
			double start = omp_get_wtime();
			multiply_matrix_parallel(size, matrix1, matrix2, result);
			double end = omp_get_wtime();
			times[i] = end - start;
		}

		std::cout << "Parallel execution : " << get_trunclated_average(count_execution, times) << std::endl;

		std::cout << "Continue?" << std::endl;
		std::cin >> exit;
	}
	

    return 0;
}