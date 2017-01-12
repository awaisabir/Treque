package comp2402a2;

import java.util.AbstractList;

/**
 * Treque : an implementation of the List interface 
 * that allows for fast modifications at the head, tail
 * and middle of the list.
 *
 * Modify the methods so that 
 *  -set/get have constant runtime
 *  -add/remove have O(1+min{i, size()-i, |size()/2-i|})
 *              amortized runtime.
 * 
 * @author morin/ArrayDeque
 * @author Muhammad Qureshi, Yaoshen Jiang/Treque
 *
 * @param <T> the type of objects stored in this list
 */
public class Treque<T> extends AbstractList<T> 
{
    //Two deques
    protected Deque<T> leftDeque;
    protected Deque<T> rightDeque;
    
    //Main to testthe treque and methods
    public static void main(String args[])
    {
		//deque size
        int total_deque_size = 14;
        
		//New treque
        Treque<Integer> sampleTreque = new Treque<Integer>(Integer.class);
        
		//FOR-LOOP to add the elements
        for(int i = 0; i < total_deque_size; i++)
        {
            sampleTreque.add(i,i);
            System.out.println("These are the elements in the leftDeque " + sampleTreque.leftDeque.n);
            System.out.println("These are the elements in the rightDeque " + sampleTreque.rightDeque.n);
        }
    }
    
    //Constructor
    public Treque(Class<T> t)
    {
        leftDeque = new Deque<T>(t);
        rightDeque = new Deque<T>(t);
    }  
    
    
    //size method
    public int size()
    {
        return (leftDeque.size() + rightDeque.size()); 
    }
    
	//get method
    public T get(int i)
    {
       if( i < 0 || i > size() ) throw new IndexOutOfBoundsException();
       
       if(i < leftDeque.size())
           return leftDeque.get(i);
       else  
           return rightDeque.get(i - leftDeque.size());          
    }
	//set method
	public T set(int i, T x)
	{
		T y;
		if( i < 0 || i > size() ) throw new IndexOutOfBoundsException();
		
		if(i < leftDeque.size())
           y = leftDeque.set(i,x);
       else  
           y = rightDeque.set(i - leftDeque.size(), x);	
	   
	   return y;
	}
    
	//remove method
    public T remove(int i)
    {
        T x; 
        if( i < 0 || i > size() ) 
            throw new IndexOutOfBoundsException();
        
        if(i < leftDeque.size())
            x = leftDeque.remove(i);
        else
            x = rightDeque.remove(i - leftDeque.size());
        
        balance();   //call balance every time an element is removed
        
        return x;
    }
    
	//add method
    public void add(int i, T x)
    {
        if ( i < 0 || i > size() )
            throw new IndexOutOfBoundsException();
        
        if (i < leftDeque.size())
            leftDeque.add(i, x);
        else
            rightDeque.add(i - leftDeque.size(), x);
        
        balance();   //call balance everytime an element is added
    }
    
	//balance method
    public void balance()
    {
        T y;
        
        if(leftDeque.size() - rightDeque.size() == 2)
        {
            y = leftDeque.remove(leftDeque.size() - 1 );
            rightDeque.add(0,y);
        }
        if(rightDeque.size() - leftDeque.size() == 2)
        {
            y = rightDeque.remove(0);
            leftDeque.add(leftDeque.size(),y);
        }
    }
}
