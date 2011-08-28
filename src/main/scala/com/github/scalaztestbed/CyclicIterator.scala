package com.github.scalaztestbed

case class CyclicIterator[T](traversable: Traversable[T]) extends Iterator[T] {
  var currentIterator = traversable.toIterator
  val syncObject = new Object()
  def next() = {
    syncObject.synchronized{
      if (!currentIterator.hasNext) currentIterator = traversable.toIterator
      currentIterator.next()
    }
  }
  def hasNext = !traversable.isEmpty
}