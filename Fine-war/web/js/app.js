/* 
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */


$(function() {
    $('#new_entry, #edit_entry').submit(function() {
        $('#hTitle').val($('#title').text());
        $('#hBody').val($('#body').text());
    });
   
    $('.votes').on('click', '.upvote.active-link', function(e) {
        var id = $(this).attr('id');
        $(this).attr('class', 'upvote');
        var self = this;
            $.post("/upvote", {'entryId': id}, function(data) {
                console.log(data);
                console.log($(self).parent().find(".number"));
                $(self).parent().find(".number").text(data.votes);
                $(self).parent().find(".glyphicon").attr('class', 'glyphicon glyphicon-heart')
        });
        e.preventDefault();
        
    })
    
    $('')
});
