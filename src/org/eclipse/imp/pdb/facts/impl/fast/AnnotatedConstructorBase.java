package org.eclipse.imp.pdb.facts.impl.fast;

import org.eclipse.imp.pdb.facts.IConstructor;
import org.eclipse.imp.pdb.facts.IValue;
import org.eclipse.imp.pdb.facts.type.Type;

/*package*/ abstract class AnnotatedConstructorBase extends Constructor {

	/*package*/ AnnotatedConstructorBase(Type constructorType, IValue[] children){
		super(constructorType, children);
	}
	
	public boolean hasAnnotations(){
		return true;
	}

	public IConstructor removeAnnotations(){
		return new Constructor(constructorType, children);
	}
	
	public boolean equals(Object o){
		if(o == this) return true;
		if(o == null) return false;
		
		if(o.getClass() == getClass()){
			AnnotatedConstructorBase other = (AnnotatedConstructorBase) o;
			
			if(constructorType != other.constructorType) return false;
			
			IValue[] otherChildren = other.children;
			int nrOfChildren = children.length;
			if(otherChildren.length == nrOfChildren){
				for(int i = nrOfChildren - 1; i >= 0; i--){
					if(!otherChildren[i].equals(children[i])) return false;
				}
				return true;
			}
		}
		
		return false;
	}
}