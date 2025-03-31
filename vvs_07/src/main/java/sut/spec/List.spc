specification List[Element]
  sorts
    List[Element]

  constructors
    make:        --> List[Element];
    addFirst:    List[Element] Element --> List[Element];

  observers
    getFirst:    List[Element] -->? Element;
    removeFirst: List[Element] -->? List[Element];
    getLast:     List[Element] -->? Element;
    removeLast:  List[Element] -->? List[Element];
    addLast:     List[Element] Element --> List[Element];
    size:        List[Element] --> int;
    get:         List[Element] int -->? Element;
    set:         List[Element] int Element -->? List[Element];
    add:         List[Element] int Element -->? List[Element];
    remove:      List[Element] int -->? List[Element];
    removelem:   List[Element] Element -->? List[Element];
    contains:    List[Element] Element;
    concat:		 List[Element] List[Element] --> List[Element];
  others
    isEmpty:     List[Element];
   
  domains
    L: List[Element]; E:Element; I: int;  
    getFirst (L)      if not isEmpty (L);
    removeFirst (L)   if not isEmpty (L);
    getLast (L)       if not isEmpty (L);
    removeLast (L)    if not isEmpty (L);
    get(L,I)          if 0 <= I and I < size(L);
    set(L,I,E)        if 0 <= I and I < size(L);
    add(L,I,E)        if 0 <= I and I < size(L);
    remove(L,I)       if 0 <= I and I < size(L);
    removelem (L,E)   if not isEmpty (L);
 
  axioms
    L, L1: List[Element];    E, F: Element; I: int;

    getFirst (addFirst (L, E)) = E;

    removeFirst (addFirst (L, E)) = L;

    isEmpty (L) iff size (L) = 0;

    size (make ()) = 0;
	size (addFirst (L, E)) = 1 + size (L);

	getLast (addFirst (L, E)) =
	  E when isEmpty(L)
	  else getLast(L);
	  
	addLast (make (), E) = addFirst (make(), E);
	addLast (addFirst (L, E), F) =
	  addFirst (addLast (L, F), E);

    removeLast (addFirst (L, E)) =
      L when isEmpty (L)
      else addFirst (removeLast (L), E);
      
    get(addFirst(L,E),I) =
      E when I = 0
      else get(L,I-1);
      
    set(addFirst(L,E),I,F) =
      addFirst(L,F) when I = 0
      else addFirst(set(L,I-1,F),E);

    removelem(addFirst(L,E),F)= L when E=F else addFirst(removelem(L,F),E);

  	not contains(make(),F);
    contains(addFirst(L,E),F) iff E=F or contains(L,F);
     
     concat(make(),L1) = L1;
     concat(addFirst(L,E),L1) = addFirst(concat(L,L1),E);
     
     add(addFirst(L,E),I,F) =
      addFirst(addFirst(L,E),F) when I = 0
      else addFirst(add(L,I-1,F),E);
    
end specification

