// 弹出隐藏
function showCL(name) {
    document.getElementById(name).style.display = 'block';
}
function closeCL(name) {
    document.getElementById(name).style.display = 'none';
}


//tab
function tabShow(m, c, n, t) {
    //m为导航统一名称，c为对应内容统一名称，n为对应序号,t为数量（比如这个切换效果有三块内容则为3）
    for (i = 1; i <= t; i++) {
        document.getElementById(m + i).className = "";
        document.getElementById(c + i).style.display = "none";
    }
        document.getElementById(m + n).className = "selected";
        document.getElementById(c + n).style.display = "";
}


