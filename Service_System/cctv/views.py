from rest_framework import status
from rest_framework.response import Response
from rest_framework.views import APIView

from cctv.models import Alert, Cctv
from cctv.serializers import AlertSerializer, CctvSerializer


# Create your views here.
class AlertApiView(APIView):
    queryset = Alert.objects.all()
    serializer_class = AlertSerializer

    def post(self, request, cctv_id):
        request.data['cctv_id'] = cctv_id
        print(request.data)
        serializer = self.serializer_class(data=request.data)
        serializer.is_valid(raise_exception=True)
        serializer.save()

        return Response(serializer.data, status=status.HTTP_201_CREATED)

    def get(self, request, cctv_id):
        alerts = Alert.objects.filter(cctv_id=cctv_id)
        serializer = self.serializer_class(alerts, many=True)

        return Response(serializer.data, status=status.HTTP_200_OK)


class CctvApiView(APIView):
    queryset = Cctv.objects.all()
    serializer_class = CctvSerializer

    def get(self, request):
        cctvs = Cctv.objects.filter(owner_id=1)
        serializer = self.serializer_class(cctvs, many=True)

        return Response(serializer.data, status=status.HTTP_200_OK)


class CctvDetailApiView(APIView):
    queryset = Cctv.objects.all()
    serializer_class = CctvSerializer

    def get(self, request, cctv_id):
        cctv = Cctv.objects.get(cctv_id=cctv_id)

        serializer = self.serializer_class(cctv)

        return Response(serializer.data, status=status.HTTP_200_OK)
