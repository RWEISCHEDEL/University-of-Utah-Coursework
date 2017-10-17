public class Node {
	
	private Node left;
	private Node right;
	public int feature;
	
	public Node(int feature, Node left, Node right){
		this.feature = feature;
		this.left = left;
		this.right = right;
	}
	
	public void setFeature(int feature){
		this.feature = feature;
	}
	
	public void setNodeLeft(Node left){
		this.left = left;
	}
	
	public void setNodeRight(Node right){
		this.right = right;
	}
	
	public int getFeature(){
		return this.feature;
	}
	
	public Node getNodeLeft(){
		return this.left;
	}
	
	public Node getNodeRight(){
		return this.right;
	}
	
}
