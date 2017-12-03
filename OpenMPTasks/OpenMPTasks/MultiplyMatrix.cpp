#include "stdafx.h"
#include "MultiplyMatrix.h"
#include <iostream>

void multiply_matrix_consistent(size_t size, int* matrix1, int* matrix2, int* result)
{
	memset(result, 0, size * size * sizeof(int));
	for (size_t i = 0; i < size; ++i)
	{
		for (size_t j = 0; j < size; ++j)
		{
			for (size_t l = 0; l < size; ++l)
			{
				result[i * size + j] += matrix1[i * size + l] * matrix2[l * size + j];
			}
		}
	}
}

void multiply_matrix_parallel(size_t size, int *matrix1, int *matrix2, int *result)
{
	memset(result, 0, size * size * sizeof(int));
	#pragma omp parallel for
	for (int i = 0; i < (int)size; ++i)
	{
		for (size_t j = 0; j < size; ++j)
		{
			for (size_t l = 0; l < size; ++l)
			{
				result[i * size + j] += matrix1[i * size + l] * matrix2[l * size + j];
			}
		}
	}
}

void fill_matrix_random(size_t size, int *matrix1)
{
	for (size_t i = 0; i < size; ++i)
	{
		for (size_t j = 0; j < size; ++j)
		{
			matrix1[i * size + j] = rand() % 10 + 1;
		}
	}
}