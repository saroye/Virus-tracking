
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

public class ComputerNode extends CommunicationsMonitor{

	/**
	 * The ComputerNode class represents the nodes of the graph G,
	 * which are pairs (Ci,t).
	 * @author Saroye
	 *
	 */
	int ci, t;
	List<ComputerNode> out = new ArrayList<ComputerNode>();
	ComputerNode next,prev,head,tail;
	public ComputerNode(int ci, int timesptamp) 
	{
		next =prev=null;
		this.ci = ci;
		this.t = timesptamp;
		out = new ArrayList<ComputerNode>();
	}

	/**
	 * Returns the ID of the associated computer.
	 * @return
	 */
	public int getID() {
		return ci;
	}
	/**
	 * Returns the timestamp associated with this node.
	 * @return
	 */
	public int getTimestamp() {
		return t;
	}
	/**
	 * Returns a list of ComputerNode objects to which there
	 * is outgoing edge from this ComputerNode object.
	 * @return
	 */
	public List<ComputerNode> getOutNeighbours()
	{	
		return out;
	}

	public ComputerNode getNext() {
		return next;
	}
	public ComputerNode getPrev() {
		return prev;
	}
}

