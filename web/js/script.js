// ��������
function showCL(name) {
    document.getElementById(name).style.display = 'block';
}
function closeCL(name) {
    document.getElementById(name).style.display = 'none';
}


//tab
function tabShow(m, c, n, t) {
    //mΪ����ͳһ���ƣ�cΪ��Ӧ����ͳһ���ƣ�nΪ��Ӧ���,tΪ��������������л�Ч��������������Ϊ3��
    for (i = 1; i <= t; i++) {
        document.getElementById(m + i).className = "";
        document.getElementById(c + i).style.display = "none";
    }
        document.getElementById(m + n).className = "selected";
        document.getElementById(c + n).style.display = "";
}


