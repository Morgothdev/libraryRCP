package libraryRCP.data;

import java.util.HashSet;
import java.util.Properties;
import java.util.Set;

public abstract class BooksDataBase implements Books {

	private Set<OnChangeDataListener> listeners = new HashSet<>();
	
	public BooksDataBase(Properties properties){}
		
	@Override
	public void registerOnChangeDataListener(OnChangeDataListener listenerToRegister) {
		listeners.add(listenerToRegister);
	}

	@Override
	public void removeOnChangeDataListener(Object listenerToRemove) {
		listeners.remove(listenerToRemove);
	}
	
	protected void notifyOnChangeDataListeners(){
		for(OnChangeDataListener listener : listeners){
			listener.onDataChange();
		}
	}
}
