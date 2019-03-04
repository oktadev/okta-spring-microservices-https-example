
$.getJSON("/classes", function (classes) {
    var $tableBody = $("#classes tbody");
    classes.forEach(function (item) {
        var $line = $("<tr>");
        $line.append( $("<td>").text(item.courseName) );
        $line.append( $("<td>").text(item.teacherName) );
        $line.append( $("<td>").text(item.year) );
        $line.append( $("<td>").text(item.numberOfStudents) );
        $tableBody.append($line);
    })
});