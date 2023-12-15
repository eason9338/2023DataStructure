
public class Keyword {
	public String name; 
  public float weight;
    
    public Keyword(String name, float weight){
		this.name = name;
		this.weight = weight;
    }
    
    @Override
    public String toString(){
    	return "["+name+","+weight+"]";
    }
    
    public String getName()
    {
    	return name;
    }
    
    public float getWeight()
    {
    	return weight;
    }
}