public class Triples extends CommunicationsMonitor{
	int ci,cj,time;
	
	public Triples(int c1,int c2, int time) {
		this.ci =c1;
		this.cj=c2;
		this.time=time;
	}
	public int getTime() {
		return time;
	}
	public int getCi() {
		return ci;
	}
	public int getCj() {
		return cj;
	}
}

