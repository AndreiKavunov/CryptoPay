package com.example.basicexample.ui

//class ReversingLinkedList<T> @SafeVarargs constructor(vararg values: T) {
//    private var head: Node? = null
//
//    init {
//        var previous: Node? = null //previous node
//        for (value in values) {
//            val node: Node = Node()
//            node.setValue(value)
//            if (previous != null) {
//                previous.setNext(node)
//            } else {
//                head = node
//            }
//            previous = node
//        }
//    }
//
//    fun reverse(): ReversingLinkedList<T> {
//        var node: Node? = head
//        var previous: Node? = null
//        while (node != null) {
//
//            //Next item.
//            val tmp: Node = node.next!!

//            //Swap items.
//            node.setNext(previous)
//            previous = node
//            head = node
//
//            //Next item.
//            node = tmp
//        }
//        return this
//    }
//
//    private inner class Node {
//        var next: Node? = null
//        var value: T? = null
//            private set
//
//        fun setValue(value: T) {
//            this.value = value
//        }
//    }
//}

val list: MutableList<Int> = mutableListOf(1, 2, 3)

fun reverse(list: MutableList<Int>){
    list.removeLast()

}

