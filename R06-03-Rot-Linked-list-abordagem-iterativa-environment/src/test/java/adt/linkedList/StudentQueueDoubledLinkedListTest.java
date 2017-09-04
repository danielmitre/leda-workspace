package adt.linkedList;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

import adt.queue.Queue;
import adt.queue.QueueDoubleLinkedListImpl;
import adt.queue.QueueOverflowException;
import adt.queue.QueueUnderflowException;

public class StudentQueueDoubledLinkedListTest {
	Queue<Integer> fila;
	
	@Before
	public void setup(){
		fila = new QueueDoubleLinkedListImpl<Integer>(6);
	}
	
	
	@Test
	public void test() {
		assertTrue(fila.isEmpty());
		assertFalse(fila.isFull());
		
		try {
			fila.dequeue();
			fail();
		} catch (QueueUnderflowException e) {
			//do nothing
		}
		
		try {
			fila.enqueue(9);
			fila.enqueue(9);
			fila.enqueue(10);
			fila.enqueue(2);
			fila.enqueue(7);
		} catch (QueueOverflowException e) {
			fail();
		}
		
		//fila: 9, 9, 10, 2, 7
		
		assertFalse(fila.isEmpty());
		assertFalse(fila.isFull());
		
		assertNotNull(fila.head());
		assertEquals(9, (int) fila.head());
		
		try {
			assertEquals(9, (int) fila.dequeue());
		} catch (QueueUnderflowException e) {
			fail();
		}
		
		//fila: 9, 10, 2, 7
		
		try {
			fila.enqueue(8);
			fila.enqueue(9999);
		} catch (QueueOverflowException e) {
			fail();
		}
		
		//fila: 9, 10, 2, 7, 8, 9999
		
		assertFalse(fila.isEmpty());
		assertTrue(fila.isFull());
		
		try {
			fila.enqueue(983);
			fail();
		} catch (QueueOverflowException e){
			//do nothing
		}
		
		try {
			assertEquals(9, (int) fila.dequeue());
			assertEquals(10, (int) fila.dequeue());
			assertEquals(2, (int) fila.dequeue());
			assertEquals(7, (int) fila.dequeue());
			assertEquals(8, (int) fila.dequeue());
			assertEquals(9999, (int) fila.dequeue());
		} catch (QueueUnderflowException e) {
			fail();
		}
		
		//fila: vazia
		
		assertTrue(fila.isEmpty());
		assertFalse(fila.isFull());
		
		try {
			fila.dequeue();
			fail();
		} catch (QueueUnderflowException e) {
			//do nothing
		}
		
	}

}
