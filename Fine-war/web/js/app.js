/* 
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */


$(function() {
    $('#new_entry').submit(function() {
        $('#hTitle').val($('#title').text());
        $('#hBody').val($('#body').text());
    });
});
