from firebase_admin import messaging
from rest_framework import status
from rest_framework.decorators import authentication_classes, permission_classes
from rest_framework.permissions import IsAuthenticated
from rest_framework.response import Response
from rest_framework.views import APIView
from rest_framework_simplejwt.authentication import JWTAuthentication

from cctv.models import Alert, Cctv
from cctv.serializers import AlertSerializer, CctvSerializer


def send_message_to(fcm_token, alert):
    message = messaging.Message(
        token=fcm_token,
        notification=messaging.Notification(
            title="의싱 상황 발생 감지",
            body=f"{alert.cctv_id_id} CCTV에서 의심 상황 발생!"
        )
    )

    response = messaging.send(message)

    print(response)


class AlertApiView(APIView):
    queryset = Alert.objects.all()
    serializer_class = AlertSerializer

    @authentication_classes([JWTAuthentication])
    @permission_classes([IsAuthenticated])
    def post(self, request, cctv_id):
        request.data['cctv_id'] = cctv_id
        serializer = self.serializer_class(data=request.data)
        serializer.is_valid(raise_exception=True)
        alert = serializer.save()

        token = request.user.tokens.first().token

        send_message_to(token, alert)

        return Response(serializer.data, status=status.HTTP_201_CREATED)

    @authentication_classes([JWTAuthentication])
    @permission_classes([IsAuthenticated])
    def get(self, request, cctv_id):
        alerts = Alert.objects.filter(cctv_id=cctv_id)
        serializer = self.serializer_class(alerts, many=True)

        return Response(serializer.data, status=status.HTTP_200_OK)


@authentication_classes([JWTAuthentication])
@permission_classes([IsAuthenticated])
class CctvApiView(APIView):
    queryset = Cctv.objects.all()
    serializer_class = CctvSerializer

    def get(self, request):
        print(request.user.id)
        cctvs = Cctv.objects.filter(owner_id=request.user.id)
        serializer = self.serializer_class(cctvs, many=True)

        return Response(serializer.data, status=status.HTTP_200_OK)


@authentication_classes([JWTAuthentication])
@permission_classes([IsAuthenticated])
class CctvDetailApiView(APIView):
    queryset = Cctv.objects.all()
    serializer_class = CctvSerializer

    def get(self, request, cctv_id):
        cctv = Cctv.objects.get(cctv_id=cctv_id)

        serializer = self.serializer_class(cctv)

        return Response(serializer.data, status=status.HTTP_200_OK)
