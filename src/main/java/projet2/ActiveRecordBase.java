package projet2;

public abstract class ActiveRecordBase {
	protected int id;
	protected boolean _builtFromDB = false;
	
	protected abstract void _update();
	protected abstract void _insert();
	protected abstract void _delete();
   
	public void save() {
		if (_builtFromDB)
			_update();
		else {
			_insert();
			_builtFromDB = true;
		}
	}
   
	public void delete() {
		_delete();
	}
   
	public int getID() {
		return id;
	}
   
	public void setId(int new_id) {
		id = new_id;
	}
}
