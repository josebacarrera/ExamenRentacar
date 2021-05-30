$(document).ready(init);

function init(){
    $('modalbox').off('click').on('click',function(event){event.stopPropagation();});
    $('modal').off('click').on('click',hidemodal);

    $('#nuevo').off('click').on('click',nuevovehiculo);

    $('.editar').off('click').on('click',editarvehiculo);

    $('#enviar').off('click').on('click',enviarvehiculo);

    $('.eliminar').off('click').on('click',eliminarvehiculo);
}

function showmodal(cliente_id){
    $.ajax({
        url:'/api/clientes',
        method:'GET',
        success:function(clientes){
            if(clientes != null){
                $('select#cliente_id option').remove();
                $('select#cliente_id').append('<option value="0">- Elegir -</option>')
                for(cliente of clientes){
                    $('select#cliente_id').append('<option value="'+cliente.id+'" '+(cliente_id==cliente.id?'selected':'')+'>'+cliente.nombre+' '+cliente.apellido+'</option>')
                }
            }
            $('modal').removeClass('hidden');
        }

    });
}

function hidemodal(){
    $('modal').addClass('hidden');
}

function nuevovehiculo(){
    $('modal input').val('');
    $('#titulomodal').text('Añadir Nuevo');
    $('#enviar').text('Añadir');

    showmodal();
}

function editarvehiculo(){
    $('modal input').val('');
    $.ajax({
        url:'/api/vehiculos/'+$(this).closest('tr').data('id'),
        method:'GET',
        success:function(vehiculo){

            $('#titulomodal').text('Editar Vehiculo');
            $('#enviar').text('Guardar');

            $('#cliente_id').val(vehiculo.cliente_id);

            $('#id_vehiculo').val(vehiculo.id);
            $('#matricula').val(vehiculo.matricula);
            $('#marca').val(vehiculo.marca);
            $('#modelo').val(vehiculo.modelo);
            $('#color').val(vehiculo.color);
            
            showmodal(vehiculo.cliente_id);
        }
    });
}

function enviarvehiculo(){
    let id_vehiculo = $('#id_vehiculo').val();

    let data = {
        matricula:$('#matricula').val(),
        marca:$('#marca').val(),
        modelo:$('#modelo').val(),
        color:$('#color').val(),
        cliente_id:$('#cliente_id').val()
    };
    


    if(id_vehiculo>0){
        // GUARDAR
        $.ajax({
            url:'/api/vehiculos/'+id_vehiculo,
            method:'PUT',
            data:data,
            success:function(response){

                if(response === true){
                    $('modal').addClass('hidden');
                    alert('Los cambios se han guardado correctamente');
                    window.location.href = window.location.href;
                }else{
                    alert('Los datos NO SE HAN GUARDADO');
                }
            },
            error:function(){
                alert('Hay un error en la llamada/respuesta AJAX');
            }
        });    
    
    }else{
        // INSERTAR
        $.ajax({
            url:'/api/vehiculos',
            method:'POST',
            data:data,
            success:function(response){
                if(response?.insertado === true){
                    $('modal').addClass('hidden');
                    alert('El nuevo vehiculo se ha insertado correctamente');
                    window.location.href = window.location.href;
                }
            },
            error:function(){
                alert('Hay un error en la llamada/respuesta AJAX');
            }
        });    
    }


}


function eliminarvehiculo(){
    let id = $(this).closest('tr').data('id');
    $.ajax({
        url:'/api/vehiculos/'+id,
        method:'DELETE',
        success:function(response){
            if(response === true) window.location.href = window.location.href;
            else alert('NO SE HA PODIDO BORRAR');

        },
        error:function(){
            alert('Hay un error en la llamada/respuesta AJAX');
        }
    });
}