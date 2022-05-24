package org.alternative.backend.base

interface RequestSampler<T> {

  T minimal()

  T full()
}
