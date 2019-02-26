conda update -n base -c defaults conda -y
conda install -n python3 bokeh dask datashader fastparquet numba python-snappy -y
conda install  -n python3 -c conda-forge dask-learn dask-ml category_encoders dask-searchcv -y


cd SageMaker