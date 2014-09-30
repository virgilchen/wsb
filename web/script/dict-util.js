/*

g$LS.loadDict = function () {
	//this.remove(key)
	this.clear() ;
	if (typeof(g$dict) == "undefined") {
		// ajaxxx
	}
	
	this.put("g$dict", g$dict) ;
} ;
*/

g$dict.get = function (_dictName) {
    if (language == null) {
        return g$dict[_dictName] ;
    }
    
    var ret = g$dict[_dictName + "_" + language] ;
    
    if (ret == null) {
        ret = g$dict[_dictName] ;
    }
    
    return ret ;
} ;