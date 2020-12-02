import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;
import java.util.concurrent.TimeUnit;


/**
 * The CommunicationsMonitor class represents the graph G 
 * built to answer infection queries. It has the following methods.
 * @author Saroye
 *
 */
public class CommunicationsMonitor
{
	int size;
	public LinkedList<ComputerNode> SortedByTime;
	ArrayList<Triples> triples;
	HashMap<Integer, List<ComputerNode>> hmap;
	LinkedList<ComputerNode> queue;
	public CommunicationsMonitor()
	{
		SortedByTime = new LinkedList<ComputerNode>();
		triples = new ArrayList <Triples>();
		hmap = new HashMap<Integer, List<ComputerNode>>();

		queue = new LinkedList<ComputerNode>();

		size =0;
	}

	/**
	 * Takes as input two integers c1, c2 and a timestamp. 
	 * This triple represents the fact that the computers with IDs 
	 * c1 and c2 have communicated at the given timestamp. 
	 * This method should run in O(1) time.
	 * @param c1
	 * @param c2
	 * @param timestamp
	 */

	public void addCommunication(int ci, int cj, int timestamp)
	{
		Triples t = new Triples(ci,cj,timestamp);
		triples.add(t);
	}

	public ArrayList<Triples>  MergeSort(ArrayList<Triples> check)
	{
		ArrayList<Triples> left = new ArrayList<Triples>();
		ArrayList<Triples> right = new ArrayList<Triples>();
		int center;
		if (check.size() == 1) {    
			return check;
		} else {
			center = check.size()/2;
			for (int i=0; i<center; i++) 
			{
				Triples t = new Triples(check.get(i).getCi(),check.get(i).getCj(),check.get(i).getTime());
				left.add(t);
			}

			for (int i=center; i<check.size(); i++) 
			{
				Triples t = new Triples(check.get(i).getCi(),check.get(i).getCj(),check.get(i).getTime());
				right.add(t);
			}

			left  = MergeSort(left);
			right = MergeSort(right);
			Merge(left, right, check);
		}
		return check;

	}
	public void Merge(ArrayList<Triples> l, ArrayList<Triples> r,ArrayList<Triples> check) 
	{
		int leftIdx = 0;
		int rightIdx = 0;
		int checkIdx = 0;
		while (leftIdx < l.size() && rightIdx < r.size()) 
		{
			if ( l.get(leftIdx).getTime() < r.get(rightIdx).getTime() )
			{
				check.set(checkIdx, l.get(leftIdx));
				leftIdx++;
			} 
			else 
			{
				check.set(checkIdx, r.get(rightIdx));
				rightIdx++;
			}
			checkIdx++;
		}

		ArrayList<Triples> rest;
		int restIdx;
		if (leftIdx >= l.size()) 
		{
			rest = r;
			restIdx = rightIdx;
		} 
		else
		{
			rest = l;
			restIdx = leftIdx;
		}

		// Copy the rest of whichever ArrayList (left or right) was not used up.
		for (int i=restIdx; i<rest.size(); i++)
		{
			check.set(checkIdx, rest.get(i));
			checkIdx++;
		}
	}

	public void createGraph(CommunicationsMonitor G, ComputerNode s)
	{
		getComputerMapping();
		boolean visited[] = new boolean[SortedByTime.size()+ hmap.get(s.getID()).size()];
		List<ComputerNode> kk = hmap.get(s.getID());
		visited[s.getID()] = true;
		queue.add(s);
		while (queue.size() != 0 )
		{
			s = queue.poll();
			System.out.print(s.getID()+ " ");
			Iterator<ComputerNode> i = s.getOutNeighbours().listIterator();

			while (i.hasNext()) 
			{ 
				ComputerNode n = i.next(); 
				if (!visited[n.getID()]) 
				{ 
					visited[n.getID()] = true; 
					queue.add(n); 

				}
			}

		}

	}
	/**
	 * Determines whether computer c2 could be infected by time y 
	 * if computer c1 was infected at time x. If so, 
	 * the method returns an ordered list of ComputerNode objects 
	 * that represents the transmission sequence. The first ComputerNode 
	 * object will correspond to c1. Similarly, the last ComputerNode object 
	 * will correspond to c2. If c2 is not infected, return null. 
	 * This method can assume that it will be called only after createGraph() and x ≤ y.
	 *  This method must run in O(m) time. This method can also be called 
	 *  multiple times with different inputs once the graph is constructed.
	 * @param c1
	 * @param c2
	 * @param x
	 * @param y
	 * @return
	 */
	public List<ComputerNode> queryInfection(int c1, int c2, int x, int y)
	{
		// can c2 be infected by time y
		//c1 is infected check his hashmap
		//
		List<ComputerNode> check = new LinkedList<ComputerNode>();
		return null;
	}

	/**
	 * Returns a HashMap that represents the mapping between an Integer 
	 * and a list of ComputerNode objects.The Integer 
	 * represents the ID of some computer Ci,
	 * while the list consists of pairs (Ci,t1),(Ci,t2),...,(Ci,tk),
	 * represented by ComputerNode objects,that specify that Ci has 
	 * communicated with other computers at times t1,t2,...,tk. 
	 * The list for each computer must be ordered by time;i.e.,t1 <t2 <···<tk.
	 * @return
	 */

	public HashMap < Integer, List<ComputerNode> > getComputerMapping()
	{
		MergeSort(triples);
		ArrayList<List<ComputerNode>> computerMapping  = new ArrayList<List<ComputerNode>>() ;

		int i, total = 0,cj,t,ci;
		for(i = 1; i < triples.size(); i++)
		{
			if( triples.get(i).getCj() > total )
			{
				total = triples.get(i).getCj();
			}
			else if( triples.get(i).getCi() > total )
			{
				total = triples.get(i).getCi();
			}
		}

		for(i =0; i < triples.size(); i++)
		{
			ci = triples.get(i).getCi();
			cj = triples.get(i).getCj();
			t  = triples.get(i).getTime();
			ComputerNode compI = new ComputerNode(ci,t);
			ComputerNode compJ = new ComputerNode(cj,t);
			if(SortedByTime.isEmpty()) 
			{
				SortedByTime.add(compI);
				compI.next = compJ;
				SortedByTime.add(compJ);
				compJ.prev = compI;
			}
			else if(SortedByTime.getLast().getTimestamp() == compI.getTimestamp() )
			{
				ComputerNode last = SortedByTime.getLast();
				if ( compI.getID() == last.getID()  ) 
				{
					last.next = compJ;
					SortedByTime.add(compJ);
					compJ.prev = last;
				}
				if ( compJ.getID() == last.getID() ) 
				{
					last.next = compI;
					SortedByTime.add(compI);
					compI.prev = last;
				}
			}
			else
			{
				compI.next = compJ;
				compJ.prev = compI;
				SortedByTime.add(compI);
				SortedByTime.getLast().next = compJ;
				SortedByTime.add(compJ);
				SortedByTime.getLast().prev = compI;
			}
		}

		for(i =0; i<SortedByTime.size(); i++) 
		{
			ComputerNode outgoing = SortedByTime.get(i);
			ComputerNode inc = SortedByTime.get(i);
			while(inc.next!=null) {

				outgoing.out.add(inc.next);
				inc = inc.next;

			}

		}
		for(i =0; i<total; i++) 
		{
			/*
			 * making list for every computer
			 */
			LinkedList<ComputerNode> compCounter = new LinkedList<ComputerNode>();
			computerMapping.add(compCounter);
		}
		for(i=0; i<SortedByTime.size(); i++) 
		{
			for(int j=0; j<total; j++) 
			{
				if(SortedByTime.get(i).getID()==j+1) 
				{
					computerMapping.get(j).add(SortedByTime.get(i));
				} 
			}
		}
		for(i=0; i<computerMapping.size(); i++) 
		{
			for(int j =0; j < computerMapping.get(i).size(); j++) 
			{
				List<ComputerNode> l =computerMapping.get(i);
				System.out.print(l.get(j).getID() +"  "+l.get(j).getTimestamp()+" ");
			}
			System.out.println(" ");
		}
		int count=1;
		for(i =0;  i<total; i++) 
		{
			hmap.put(count, computerMapping.get(i));
			count++;
		}
		//	System.out.println(hmap.values());
		return hmap;
	}

	/**
	 * Returns the list of ComputerNode objects associated with computer c by performing a lookup in the mapping.
	 * @param c
	 * @return
	 */

	public List<ComputerNode> getComputerMapping(int c)
	{
		for(int i =0; i < this.getComputerMapping().size(); i++)
		{
			if(this.getComputerMapping().containsKey(c))
			{
				return this.getComputerMapping().get(i);
			}
		}
		return null;
	}
	public  List<ComputerNode> sortedNodes(){
		return SortedByTime;
	}
	public static void main(String[] args) throws InterruptedException {
		CommunicationsMonitor cc = new CommunicationsMonitor();
		cc.addCommunication(1, 4, 12);
		cc.addCommunication(2, 4, 8);
		cc.addCommunication(1, 2, 4);
		cc.addCommunication(3, 4, 8);
		cc.getComputerMapping();
		cc.createGraph(cc, cc.SortedByTime.get(0) );
//		l.get(leftIdx).getTime() < r.get(rightIdx).getTime();
//
//		System.out.println("size of list "+ list.size());
//		cc.getComputerMapping();
//		for(int i =0; i<list.size(); i++) {
//			System.out.println("ID: "+ list.get(i).getID()+" time stamp: "+list.get(i).getTimestamp());
//		}
//		System.out.println("------------------------");
//		for(int i =0; i<list.size(); i++) 
//		{
//			System.out.println("sorted ID: "+ SortedByTime.get(i).getID()+" D's time stamp: "+SortedByTime.get(i).getTimestamp());	
//		}
//		ComputerNode n = SortedByTime.get(4).next;
//		if(SortedByTime.get(2).next.next!=null)
//		{
//			System.out.println("next ID:  "+ SortedByTime.get(2).next.next.getID()+"  next time: "+SortedByTime.get(2).next.next.getTimestamp());
//		}
//		else {System.out.println("next is still null");}
//		System.out.println("last "+ list.getLast().getID()+ " time: "+ list.getLast().getTimestamp());
//		System.out.println("previous of last "+ list.getLast().prev.getID()+ " time: "+ list.getLast().prev.getTimestamp());

	}
}